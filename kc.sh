#!/bin/bash

# compile a kotlin file, binds libraries, and run the app with args
# usage:
# kc.sh Yourcode.kt
# kc.sh Yourcode.kt alibrary.jar
# kc.sh Yourcode.kt alibrary.jar anotherlibrary.jar
# kc.sh Yourcode.kt alibrary.jar anotherlibrary.jar -- -a argsforyourapp

file=$1

i=1
for var in "${@:2}"
do
    if [[ $var == "--" ]]; then
        break
    fi
    ((i++))
done

# if there was a -- it is followed by args
if ((i < $#)); then
    lib="${@:2:((i - 1))}"
    args="${@:((i + 2))}"
else
    lib="${@:2}";
    args=""
fi

# separator between the library names: replace spacees by double quotes
if [[ "$lib" != "" ]]; then
    lib=${lib// /:}
fi

# split file name by dot
arr=(${file//./ })

name=${arr[0]}
sufix=${arr[1]}

out="${name}.jar"
class="${name^}${sufix^}"

classpath=""
if [ "$lib" != "" ];then
    classpath=" -cp $lib"
fi

# compile if source is newer than jar
if [[ "$file" -nt "$out" ]]; then
    if test -f "$out"; then
        rm $out
    fi

    command="kotlinc $file $classpath -include-runtime -d $out"
    echo "$command ..."
    $command
fi

if [ "$classpath" != "" ]; then
    command="java $classpath:$out $class $args"
else
    command="java -jar $out $args"
fi

if test -f "$out"; then
    echo "$command ..."
    $command
fi
