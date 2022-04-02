from rest import LogRest as logRest


if __name__ == '__main__':

    lor = logRest
    app = lor.get_flask_app()

    app.run(port=5000, host="0.0.0.0")
