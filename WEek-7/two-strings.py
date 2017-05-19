tests = int(input())
for test in range(tests):
    a = input()
    b  = input()
    shared = False
    
    aChars = set()
    for char in a:
        aChars.add(char)
        
    for char in b:
        if char in aChars:
            shared = True
            
    if(shared):
        print('YES')
    else:
        print('NO')
