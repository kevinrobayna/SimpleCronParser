# SimpleCronParser

This is a very simple cron parser which supports a small subset of possible cron expressions that you could use the real world. 

To see if you cron expression is valid please use https://crontab.guru

## What is a Cron Expression?
To avoid duplication of information please read https://en.wikipedia.org/wiki/Cron

## Supported Cron Expressions
* Simple ranges (`3-5`), if either of the start of the end is not a string it will default to 0
* Lists i.e `1,2,3,4,5,6` or `1,5,10,15`
* Simple Stepped ranges i.e. `*/15` which means every 15 `X` where `X` can be minutes, hours, days, etc
* Digits i.e. `0` so that you can say at instance 0 i.e. `0 */15 * * *` translates to `At minute 0 past every 15th hour` 

## What is not supported
* Complex stepped ranges:
  * `*/2,3` which means `every 2nd minute and 3` when it's used in the minute field.
  * `0-20/2` which means `every 2nd minute from 0 through 20` when it's used in the minute field.
* Nonstandard predefined scheduling definitions i.e. @yearly, @monthly, etc
* Non-Numeric Ranges for days of the week i.e. `L-F` or months i.e. `JAN–DEC`

## Usage

### Pre-requisites
* Install https://brew.sh to easy manage mac or linux binaries
* once you have brew installed execute `brew install java`
* clone repository as it's not available in any artifact manager

### How to run application
In order to run script you need to compile it first to do so you should run `./gradlew clean build`

Then to run the command the executable you should run `java -jar build/libs/cron-parser-1.0.jar "*/15 0 1,15 * 1-5 /usr/bin/find"`

### How to run tests

`./gadlew clean build` will run all the tests in the terminal