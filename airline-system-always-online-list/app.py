from rest import ListRest as listRest

if __name__ == '__main__':

    lr = listRest

    app = lr.get_flask_app()

    app.run(port=5000, host="0.0.0.0")
