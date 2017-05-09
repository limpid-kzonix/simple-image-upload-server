#!/usr/bin/env bash

sbt clean playGenerateSecret  stage  universal:packageZipTarball
