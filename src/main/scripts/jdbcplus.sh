#!/bin/bash
cp1=$(echo lib/*.jar | tr ' ' ':')
cp2=./jdbcdrivers
java -classpath ${cp1}:${cp2} com.danidemi.jdbcplus.JdbcPlus

