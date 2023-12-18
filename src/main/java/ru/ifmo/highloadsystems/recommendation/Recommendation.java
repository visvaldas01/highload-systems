package ru.ifmo.highloadsystems.recommendation;

import ru.ifmo.highloadsystems.model.entity.Song;
import ru.ifmo.highloadsystems.repository.SongRepository;

import java.util.Collection;
import java.util.List;

public class Recommendation {
    private static final float dtAdd = 0.01f;

    private final Collection<Song> history;
    private final SongRepository songRepository;
    private float MinV1 = 10000;
    private float MaxV1 = 0;
    private float MinV2 = 10000;
    private float MaxV2 = 0;
    private float MinV3 = 10000;
    private float MaxV3 = 0;

    public Recommendation(Collection<Song> library, SongRepository songRep) {
        history = library;
        songRepository = songRep;
        for (Song s : history) {
            if (s.getVector1() < MinV1) MinV1 = s.getVector1();
            if (s.getVector2() < MinV2) MinV2 = s.getVector2();
            if (s.getVector3() < MinV3) MinV3 = s.getVector3();

            if (s.getVector1() > MaxV1) MaxV1 = s.getVector1();
            if (s.getVector2() > MaxV2) MaxV2 = s.getVector2();
            if (s.getVector3() > MaxV3) MaxV3 = s.getVector3();
        }
    }

    public Song GetNextSong(int start_dt) {
        float dt = start_dt;
        while (true) {
            List<Song> possibleSongs =
                    songRepository.findByVector1BetweenAndVector2BetweenAndVector3Between(
                            MinV1 - dt,
                            MaxV1 + dt,
                            MinV2 - dt,
                            MaxV2 + dt,
                            MinV3 - dt,
                            MaxV3 + dt);
            if (possibleSongs.isEmpty())
            {
                List<Song> allSongs = songRepository.findAll();
                return allSongs.get(0);
            }
            for (Song s : possibleSongs) {
                if (history.contains(s)) continue;
                history.add(s);
                return s;
            }
            dt += dtAdd;
        }
    }
}
