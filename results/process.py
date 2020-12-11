#   METHOD_DEFINE!
#   A-->
#   B-->
GRAM_TYPE = 'B'


import os


WORKING_PATH = os.getcwd()



file_list = os.listdir(WORKING_PATH+'\\trigram'+GRAM_TYPE+'\\')
file_list.remove('_SUCCESS')
all_words = set()
all_trigrams = {}
all_bigrams = {}


past_word = ''

for fname in file_list:
    file_gram = open(WORKING_PATH+'\\trigram'+GRAM_TYPE+'\\'+fname,'r',encoding = 'utf-8')

    for line in file_gram.readlines():
        s = line.split()
        a,b,c,d = *s,
        all_words.add(a),all_words.add(b),all_words.add(c)
        all_trigrams[(a,b,c)]=int(d)
    del a,b,c,d,s
    file_gram.close()
    
    file_count = open(WORKING_PATH+'\\tricount'+GRAM_TYPE+'\\'+fname,'r',encoding = 'utf-8')
    for line in file_count.readlines():
        s = line.split()
        a,b,c = *s,
        all_words.add(a),all_words.add(b)
        all_bigrams[(a,b)]=int(c)
    del a,b,c,s
    file_count.close()
