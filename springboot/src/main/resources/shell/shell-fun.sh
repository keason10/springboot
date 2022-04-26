#!/bin/bash

function prepend(){
  [ -d "$2" ] && eval $1=\"$2\${$1:+':'\$$1\}\" && export $1;}
}