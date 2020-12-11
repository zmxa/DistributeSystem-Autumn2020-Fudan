import re,os
import numpy as np

#Same regex as in mapreduce.
CR = re.compile("([^\u4e00-\u9fa5])+")

#current working path.
WORKING_PATH = os.getcwd()

#Indeed, there are lot's of libs for python to finish Chinese words division.
#To avoid the inconsistency with JAVA implementation, GRAM_TYPE='A' is not implemented here.
GRAM_TYPE = 'B'

#open gram files and save here.
gram_file_list=[]

#open count files and save here. i.e. for (a,b,c), count (a,b,*).
count_file_list=[]

#Just let the word which never appears has the propability of 1/total words. i.e. add-one smooth.
#Which will cause a significant overfitting on the training set.
#It can be solved by using methods like Kneser-Ney smoothing.
#However, why not just use deep learning.
aos = 0
with open(WORKING_PATH+r'\distinct'+GRAM_TYPE+'\\'+'part-r-00000','r',encoding='utf-8') as fp:
    aos = fp.readline().split()[3]
    aos = int(aos)

#wordcount.
wcfp = 0
countall = 0

def init_main():
    ''' open all tri-gram files. '''
    global countall,wcfp
    a=[]
    b=[]
    i=0
    n = os.listdir(WORKING_PATH+'\\trigram'+GRAM_TYPE+'\\')
    for file in n:
        a.append(open(WORKING_PATH+'\\trigram'+GRAM_TYPE+'\\'+file,encoding='utf-8'))
        gram_file_list.append(a[i].readlines())
        a[i].close()
        b.append(open(WORKING_PATH+'\\tricount'+GRAM_TYPE+'\\'+file,encoding='utf-8'))
        count_file_list.append(b[i].readlines())
        b[i].close()
        i+=1
    t = open(WORKING_PATH+r'\wordcount'+GRAM_TYPE+'\\'+'part-r-00000','r',encoding='utf-8')
    wcfp = t.readlines()
    for a in wcfp:
        countall+=int(a.split()[1])
    t.close()
    
   
def hc(s):
    '''input a string, return its .hashCode().\nstring-->int\n'''
    seed = 31
    h = 0
    for c in s:
        h = int(seed * h) + ord(c)
    return h & 0x7FFFFFFF

def process(line):
    '''replace all none-chinese words with "&", remove "&" at the beginning.'''
    line = re.sub(CR,'&',line)
    line = line.lstrip('&')
    return line

def hello():
    print("Hello!\n输入中文并得到句子的出现概率预测！")
    print("建议输入在十个字以内！句末不必标点！")
    print("输出的概率是对数形式的，也即数值越小可能更小")
    print("这是一个基于Tri-gram的预测！")
    print("用ctrl-c 来结束吧！")

def lp3(a,b,c):
    trigram = a+' '+b+' '+c
    bigram = a+' '+b
    no = hc(a)%24
    t1,t2 = '',''
    for g in count_file_list[no]:
        if(g.startswith(bigram)):
            t2 = g
    if(t2 == ''):
        return 1/aos
    t2 = int(t2.split()[2])
    
    for g in gram_file_list[no]:
        if(g.startswith(trigram)):
            t1 = g
            break
    if(t1 == ''):
        return 1/(aos+t2)
    t1 = int(t1.split()[3])
    return (t1+1)/(aos+t2)

def lp2(a,b):
    bigram = a+' '+b
    no = hc(a)%24
    for g in count_file_list[no]:
        if(g.startswith(bigram)):
            t2 = g
    if(t2 == ''):
        return 1/(aos+countall)
    t2 = int(t2.split()[2])
    return (t2+1)/(aos+countall)   

def lp1(a):
    global countall
    t0 = ''
    for g in wcfp:
        if(g.startswith(a)):
            t0 = g
            break
    if(t0 == '' ):
        return 1/(countall+1)
    return int(t0.split()[1])/(countall+1)
    
def cleanup():
    global count_file_list,gram_file_list
    del count_file_list,gram_file_list

def guess(a,b):
    bigram = a+' '+b
    no = hc(a)%24
    gindex = -1
    for g in gram_file_list[no]:
        gindex+=1
        if(g.startswith(bigram)):
            t2 = g
            break
    if(t2 == ''): return[",",".","?","!"]
    guessli = []
    while(gindex<len(gram_file_list[no])):
        if(gram_file_list[no][gindex].startswith(bigram)):
            templi = gram_file_list[no][gindex].split()
            guessli.append((templi[2],int(templi[3])))
            gindex+=1
        else: break
    if guessli == []: return[",",".","?","!"]
    guessli.sort(key=lambda x :x[1],reverse=True)
    return guessli[0:5]
    
def main():
    hello()
    try:
        while(True):    
            line = input(">>> ")
            line = process(line)
            len_of_line = len(line)
            if(len_of_line<=1): 
                print("需要更长的句子！")
                continue
            total_probability = np.log(lp1(line[0]))+np.log(lp2(line[0],line[1]))
            for i in range(len_of_line-2):
                total_probability+=np.log(lp3(line[i],line[i+1],line[i+2]))
            print("水平大概是 %.8g" % total_probability)
            for item in guess(line[len_of_line-2],line[len_of_line-1]):
                print(item,end=' ');
            print()
                
            
    finally:
        cleanup()
       
    
    


if __name__ == "__main__":
    init_main()
    main()
