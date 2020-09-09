from PIL import Image
from os import listdir, rename, path

#bgs = [f for f in listdir("./drawable-nodpi") if "bg_" in f]
imgs = listdir("./drawable-nodpi")

i = 0
for img in imgs:
    names = img.split(".")
    image = Image.open("./drawable-nodpi/"+img)
    image.thumbnail((170, 200))
    image.save('./drawable-nodpi/thumb_bg_'+str(i) + "." + names[1])
    rename(path.join("./drawable-nodpi", img), path.join("./drawable-nodpi", "bg_" + str(i) + "." + names[1]))
    i += 1

print(imgs)