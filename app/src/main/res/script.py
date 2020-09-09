from PIL import Image
from os import listdir, rename, path

FOLDER = "./drawable-nodpi/"

#bgs = [f for f in listdir("./drawable-nodpi") if "bg_" in f]
imgs = listdir(FOLDER)

i = 0
for img in imgs:
    names = img.split(".")
    image = Image.open(FOLDER+img)
    image.thumbnail((170, 200))
    image.save(FOLDER + 'thumb_bg_' +str(i) + "." + names[1])
    rename(path.join(FOLDER, img), path.join(FOLDER, "bg_" + str(i) + "." + names[1]))
    i += 1

print(imgs)