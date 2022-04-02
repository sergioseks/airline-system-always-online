import smtplib
import os

from injector import inject
from pprint import pprint


class SendEmail(object):

    def __init__(self):
        self.message = ""
        self.subject = ""
        self.server = None
        self.user_email = '%s' % (os.environ['USER_EMAIL'])
        self.user_password = '%s' % (os.environ['USER_PASSWORD'])

        self.init_email()

    def init_email(self):
        self.message = 'El pago del vuelo se ha realizado con exito. Gracias por confiar en nosotros.'

        self.subject = 'Correo de confirmacion de pago'

        self.message = 'Subject: {}\n\n{}'.format(self.subject, self.message)

        self.server = smtplib.SMTP("smtp.gmail.com", 587)

    @inject
    def send_email(self, destination: str):
        self.server.starttls()
        # self.server.login("sec.cloud.business.solutions@gmail.com", "157c25Dc$")
        # self.server.sendmail("sec.cloud.business.solutions@gmail.com", destination, self.message)
        self.server.login(self.user_email, self.user_password)
        self.server.sendmail(self.user_email, destination, self.message)
        self.server.quit()
