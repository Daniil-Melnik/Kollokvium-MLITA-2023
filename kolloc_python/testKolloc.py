
from os import listdir
import time

fNames = ["1305.08.txt","1305.11.txt","1305.12.txt","1305.13.txt","1307.1.txt","1307.4.txt","1307.7.txt","1307.8.txt","1307.13.txt","1307.15.txt","1307.16.txt","1307.23.txt","1308.2.txt","1308.3.txt","1308.5.txt","1308.10.txt","1308.11.txt","1308.21.txt","1308.14.txt","1308.17.txt","1308.26.txt","1306.10.txt","1306.12.txt","1306.16.txt","1306.22.txt","1306.23.txt"]
DNF_str = []
Alphabet = [True for i in range(26)]

fNames = listdir("C:/Users/danii/OneDrive/Рабочий стол/JavaVScode/kolloc_python/tests")

def Function_find(fname):
    start_time = start = time.perf_counter()
    f = open("C:/Users/danii/OneDrive/Рабочий стол/JavaVScode/kolloc_python/tests/"+fname, "r")
    try:
        n=0
        while line := f.readline():
            if(line[0]!="#"):
                DNF_str.append(line.replace('\n', '.'))
                n+=1
        DNF_str[len(DNF_str)-1] = DNF_str[len(DNF_str)-1]+"."
        DNF = [False for i in range(n)]
        blockCH = []
        for i in range(n):
            dnf_one = DNF_str[i]
            if(dnf_one[1]=='_'):
                blockCH.append(dnf_one[0])
        for i in range(n):
            dnf_one = DNF_str[i]
            if dnf_one[0]=='_':
                k=1
                while(dnf_one[k] in blockCH):
                    k+=1
                Alphabet[ord(dnf_one[k])-ord('a')] = False
        for i in range(n):
            k=0
            dnf_one=DNF_str[i]
            while (dnf_one[k]!='_'):
                DNF[i] = DNF[i] or Alphabet[ord(dnf_one[k])-ord('a')]
                k+=1
            k+=1
            while(dnf_one[k]!='.'):
                DNF[i] = DNF[i] or (not Alphabet[ord(dnf_one[k])-ord('a')])
                k+=1
        res = True
        for i in range(n):
            #print(str(i)+" "+str(DNF[i]))
            res = res and DNF[i]
        print (str(res)+" "+fname+" "+str(time.perf_counter() - start_time))
    except IOError:
        print ("Could not open file! Please close Excel!")

for fName in fNames:
    Function_find(fName)