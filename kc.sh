#!/bin/bash
function kc {
    file=$1
    lib=$2
    arr=(${file//./ })

    name=${arr[0]}
    sufix=${arr[1]}

    out="${name}.jar"
    class="${name^}${sufix^}"

    if [ "$lib" != "" ];then
        if test -f "$out"; then
            rm $out
        fi
        echo "kotlinc $file -cp $lib -include-runtime -d $out ..."
        kotlinc $file -cp $lib -include-runtime -d $out
        if test -f "$out"; then
            echo "java -cp $lib:$out $class ..."
            java -cp $lib:$out $class
        fi
    else
        if test -f "$out"; then
            rm $out
        fi
        echo "kotlinc $file -include-runtime -d $out ..."
        kotlinc $file  -include-runtime -d $out
        if test -f "$out"; then
            echo "java -jar $out ..."
            java -jar $out
        fi
    fi
}

kc $1 $2
