import axios from "axios";
import Cookies from "js-cookie";
import constants from "../constants";

const token = Cookies.get("JSESSIONID");

class MediaRepository {

    static async upload(media) {
        var http = new XMLHttpRequest();
        var url = constants.API + '/media';
        http.open('POST', url, true);
        http.setRequestHeader('Content-type', 'multipart/form-data');
        http.setRequestHeader('JSESSIONID', token);
        http.withCredentials = true;
        http.send(media);

        return http.response;
    }

    static async delete(url) {
      return axios.delete(`${constants.API}/media`, {
        fileUrl: url
      });
    }
}

export default MediaRepository;
