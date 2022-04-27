#!/bin/bash

function prepend(){
  [ -d "$2" ] && eval $1=\"$2\${$1:+':'\$$1\}\" && export $1;}
}

:'
1--- 和 2 -- 表明:+不改变原值，当tx不为空，就执行后边的 'hello'$tx 语句
1---
tx='222'
echo ${tx:+'hello'$tx}
echo $tx
  hello222
  222

2--
tx=''
echo ${tx:+'hello'$tx}
echo $tx
  ''
  ''
'