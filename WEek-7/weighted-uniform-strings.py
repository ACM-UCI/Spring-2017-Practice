s = input().strip()
maxLengths = [0 for i in range(26)]

currChar = s[0]
currCount = 1
for i in range(1, len(s)):
    if s[i] == currChar:
        currCount += 1
    else:
        index = ord(currChar) - ord('a')
        maxLengths[index] = max(maxLengths[index], currCount)
        currChar = s[i]
        currCount = 1

index = ord(currChar) - ord('a')
maxLengths[index] = max(maxLengths[index], currCount)        
        
vals = set()
for i in range(26):
    #print(i, maxLengths[i])
    for j in range(1, maxLengths[i] + 1):
        vals.add((i + 1)*j)
#print(vals)

n = int(input().strip())
for a0 in range(n):
    x = int(input().strip())
    #print(x)
    if x in vals:
        print('Yes')
    else:
        print('No')
