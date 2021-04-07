import os
import sys
from PyQt5.QtWidgets import QWidget, QApplication, QFileDialog, QMessageBox
from PyQt5 import QtCore
from PyQt5.uic import loadUi
from mq_communication import RabbitMq


def debug_trace(ui=None):
    from pdb import set_trace
    QtCore.pyqtRemoveInputHook()
    set_trace()
    # QtCore.pyqtRestoreInputHook()
class AddApp(QWidget):
    ROOT_DIR = os.path.dirname(os.path.abspath(__file__))

    def __init__(self,parinte):
        super(AddApp, self).__init__()
        self.parintele=parinte
        ui_path = os.path.join(self.ROOT_DIR, 'exemplul_2.1.ui')
        loadUi(ui_path, self)
        self.save_book_btn.clicked.connect(self.save_book)
        self.rabbit_mq = RabbitMq(self)

    def save_book(self):
        name_string = self.Book_name_bar.text()
        author_string=self.Book_author_bar.text()
        publisher_string=self.Book_publisher_bar.text()
        text_string=self.Book_text_bar.toPlainText()
        request = None
        if name_string and author_string and publisher_string and text_string:
                request = 'publish:title={}*author={}*publisher={}*text={}'.format(name_string,author_string,publisher_string,text_string)
                self.parintele.send_request(request)



class LibraryApp(QWidget):
    ROOT_DIR = os.path.dirname(os.path.abspath(__file__))

    def __init__(self):
        super(LibraryApp, self).__init__()
        ui_path = os.path.join(self.ROOT_DIR, 'exemplul_2.ui')
        loadUi(ui_path, self)
        self.search_btn.clicked.connect(self.search)
        self.add_btn.clicked.connect(self.add)
        self.save_as_file_btn.clicked.connect(self.save_as_file)
        self.rabbit_mq = RabbitMq(self)

    def set_response(self, response):
        self.result.setText(response)

    def send_request(self, request):
        self.rabbit_mq.send_message(message=request)
        self.rabbit_mq.receive_message()

    def add(self):
        dialog = AddApp(self)
        dialog.show()

    def search(self):
        search_string = self.search_bar.text()
        request = None
        if not search_string:
            if self.json_rb.isChecked():
                request = 'print:json'
            elif self.html_rb.isChecked():
                request = 'print:html'
            elif self.xml_rb.isChecked():
                request = 'print:xml'
            else:
                request = 'print:raw'
        else:
            if self.author_rb.isChecked():
                if self.json_rb.isChecked():
                    request = 'find:author={}'.format(search_string+" "+"json")
                elif self.html_rb.isChecked():
                    request = 'find:author={}'.format(search_string+" "+"html")
                elif self.xml_rb.isChecked():
                    request = 'find:author={}'.format(search_string+" "+"xml")
                else:
                    request = 'find:author={}'.format(search_string+" "+"raw")
            elif self.title_rb.isChecked():
                if self.json_rb.isChecked():
                    request = 'find:title={}'.format(search_string+" "+"json")
                elif self.html_rb.isChecked():
                    request = 'find:title={}'.format(search_string+" "+"html")
                elif self.xml_rb.isChecked():
                    request = 'find:title={}'.format(search_string+" "+"xml")
                else:
                    request = 'find:title={}'.format(search_string+" "+"raw")
            else:
                if self.json_rb.isChecked():
                    request = 'find:publisher={}'.format(search_string+" "+"json")
                elif self.html_rb.isChecked():
                    request = 'find:publisher={}'.format(search_string+" "+"html")
                elif self.xml_rb.isChecked():
                    request = 'find:publisher={}'.format(search_string+" "+"xml")
                else:
                    request = 'find:publisher={}'.format(search_string+" "+"raw")
        self.send_request(request)

    def save_as_file(self):
        options = QFileDialog.Options()
        options |= QFileDialog.DontUseNativeDialog
        file_path = str(
            QFileDialog.getSaveFileName(self,
                                        'Salvare fisier',
                                        options=options))
        if file_path:
            file_path = file_path.split("'")[1]
            if not file_path.endswith('.json') and not file_path.endswith(
                    '.html') and not file_path.endswith('.txt'):
                if self.json_rb.isChecked():
                    file_path += '.json'
                elif self.html_rb.isChecked():
                    file_path += '.html'
                elif self.xml_rb.isChecked():
                    file_path += '.xml'
                else:
                    file_path += '.txt'
            try:
                with open(file_path, 'w') as fp:
                    if file_path.endswith(".html"):
                        fp.write(self.result.toHtml())
                    else:
                        fp.write(self.result.toPlainText())
            except Exception as e:
                print(e)
                QMessageBox.warning(self, 'Exemplul 2',
                                    'Nu s-a putut salva fisierul')


if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = LibraryApp()
    window.show()
    sys.exit(app.exec_())
