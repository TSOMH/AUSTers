from java import jclass
import requests;
def get_http(url):
    r=requests.get(url)

    return r.text