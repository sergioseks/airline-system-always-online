# base image
FROM node:lts-alpine

LABEL maintainer sergioseks

# set working directory
#RUN mkdir /usr/src/app
WORKDIR /usr/src/app

# add .bin to $PATH
ENV PATH /usr/src/app/node_modules/.bin:$PATH

# install package.json
COPY package.json /usr/src/app/package.json

RUN npm install

RUN npm install -g @angular/cli

# add app
COPY . /usr/src/app

# start app
CMD ng serve --host 0.0.0.0 --disable-host-check