from timeit import timeit
from os import listdir
import threading

timeout_time = 5 #this may be changed later

def find(filename):
    DNF_str = []
    Alphabet = [True for i in range(26)]
    f = open("C:/Users/danii/OneDrive/Рабочий стол/JavaVScode/kolloc_python/tests/"+filename, "r")
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
    except IOError:
        print ("Could not open file! Please close Excel!")


def find_timeout(filename):
    p = threading.Thread(target = find, args = (filename,))
    p.start()
    p.join(timeout_time)


def main():
    files = listdir("C:/Users/danii/OneDrive/Рабочий стол/JavaVScode/kolloc_python/tests")
    # first double file is necessary for the corret time check
    times = [timeit(lambda: find_timeout("C:/Users/danii/OneDrive/Рабочий стол/JavaVScode/kolloc_python/tests/"+file), number = 1) for file in [files[0]]+files][1:]
    print(*[time if time < timeout_time else "inf" for time in times])


if __name__ == "__main__":
    main()