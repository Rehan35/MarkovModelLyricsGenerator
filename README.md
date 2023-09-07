# MarkovModelLyricsGenerator
Through the use of Markov Models, I used a kaggle dataset of Drake lyrics to create an application that can generate a Drake song

# How does it work
I imported a text file of all the lyrics from Drake songs, and used my program to parse through the text file and adds each word to a string array.\
From here, I created the Markov Model method, which takes the input of one word and creates a HashMap in which all the words that follow the input word are added and the counts of the number of times they occur after the input word is the value.\ (Lyric after input word : Number of times it occurs after input word).\
Through this process, I add up all the counts together to get the total number of words directly after the input word and then I traverse through the map and update each key with the percentage of how often the key occurs after the input word.\
Finally, I generate a random percentage and I go through each key and find the closest percentage pairing to the random percentage generated.

# Updates
This random walk worked well for the song generation, but the lyrics could make a lot more sense.\
I want to implement a similar model, but the key wil be the next two words after the input word and make the lyrics more cohesive.\\

Challenges: the main challenge is the scarcity of similar pair words being used after a certain lyric.\
If only one or two exist, it may cause lyrics that are very close to matching the song word for word.
