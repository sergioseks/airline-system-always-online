from builtins import object

from models.Email import Email
from utils.SendEmail import SendEmail as send_mail
from injector import Injector, inject


class EmailService(object):

    def __init__(self):
        self.server_email = Injector().get(send_mail)

    @inject
    def send_email(self, email: Email):

        self.server_email.send_email(email.get_traveler_email())

        return email.get_email_dict()
