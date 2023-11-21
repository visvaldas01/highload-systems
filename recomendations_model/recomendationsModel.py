import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.cluster import KMeans
from sklearn.preprocessing import StandardScaler

df = pd.read_csv("./spotify_table.csv")

df = df.drop(["id", "name", "artists", "duration_ms", "release_date", "mode", "key"], axis=1)

scaled_df = StandardScaler().fit_transform(df)

kmeans_kwargs = {
"init": "random",
"n_init": 10,
"random_state": 1,
}

#create list to hold SSE values for each k
sse = []
for k in range(100, 1000):
    kmeans = KMeans(n_clusters=k, **kmeans_kwargs)
    kmeans.fit(scaled_df)
    sse.append(kmeans.inertia_)

plt.plot(range(100, 1000), sse)
plt.xticks(range(100, 1000))
plt.xlabel("Number of Clusters")
plt.ylabel("SSE")
plt.show()