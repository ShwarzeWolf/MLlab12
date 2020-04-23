import pandas as pd
from sklearn.preprocessing import LabelEncoder
from sklearn.preprocessing import OneHotEncoder
from sklearn import preprocessing

data = pd.read_csv("data.csv", header=0)

X = data.iloc[:, 0:12]
Y = data.iloc[:, 12:]

X = pd.get_dummies(X)
X = pd.DataFrame(preprocessing.normalize(X))

Y = pd.get_dummies(Y)

mass = [X, Y]
data = pd.concat(mass, axis = 1)

data.to_csv("./OneHotNormalised.csv")



blue - 0
green - 1
yellow - 2

blue green yellow
1.0   0.0    0.0
0.0    1.0    0.0
0.0    0.0    1.0
