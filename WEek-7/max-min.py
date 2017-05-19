n = int(input())
k = int(input())
a = []

for i in range(n):
    a.append(int(input()))
a.sort()

ans = min([a[i + (k - 1)] - a[i] for i in range(n - (k - 1))])
print(ans)
