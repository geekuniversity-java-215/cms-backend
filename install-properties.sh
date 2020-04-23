#!/bin/sh

fromPath=core/src/main/resources/properties-default/

for f in ${fromPath}*.default; do

    #This line splits the file name on the last delimiter "."
#baseName=`echo $f | cut -d "." -f 1`
    baseName=`echo $f | grep -Po '.*(?=\.)'`
    #echo "baseName "${baseName}
    #one level up
    toPath=`dirname "${baseName}"`/..
    #echo "toPath "${toPath}
    echo "copy_rename "$f
    cp $f ${baseName}
    # do not move over existing file
    mv -n ${baseName} ${toPath}
done