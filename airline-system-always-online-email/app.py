from rest import EmailRest as emailRest


if __name__ == '__main__':

    er = emailRest
    app = er.get_flask_app()

    app.run(port=5000, host="0.0.0.0")
