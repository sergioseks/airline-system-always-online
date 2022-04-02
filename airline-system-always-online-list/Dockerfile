FROM python:3.7-alpine

RUN mkdir /app

WORKDIR /app

COPY requirements.txt /tmp/requirements.txt
RUN apk --update add python py-pip openssl ca-certificates py-openssl wget bash linux-headers
RUN apk --update add --virtual build-dependencies libffi-dev openssl-dev python-dev py-pip build-base \
  && pip install --upgrade pip \
  && pip install --upgrade pipenv\
  && pip install --upgrade -r /tmp/requirements.txt\
  && apk del build-dependencies

COPY . /app
ENV PYTHONPATH=/app
ENV FLASK_ENV=production
ENV FLASK_APP=__init__.py

EXPOSE 5001

RUN ["pip", "install", "flask"]
CMD ["flask", "run", "--host", "0.0.0.0", "--port", "5000"]