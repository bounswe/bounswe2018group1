import axios from "axios";
import Cookies from "js-cookie";
import constants from "../constants";

const token = Cookies.get("JSESSIONID");
axios.defaults.headers.common["Content-Type"] = "application/json";
axios.defaults.headers.common["JSESSIONID"] = token;

//Change once implemented
class AnnotationRepository {

    static async getAnnotation(annotationId) {
      const response = await axios(
        {
          method: 'GET',
          url: `${constants.ANNOTATION_API}/annotation`,
          withCredentials: true,
          headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
          },
          params:{
            annotationId: annotationId
          }
        }
      )
      return response.data;
    }

    static async getAllAnnotations(itemId) {
      const response = await axios(
        {
          method: 'GET',
          url: `${constants.ANNOTATION_API}/annotation/all`,
          withCredentials: true,
          headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
          },
          params:{
            itemId: itemId
          }
        }
      )
      return response.data;
    }

    static async postAnnotation(annotationText, endIndex, itemId, startIndex, type, userId) {
        const response = await axios(
          {
            method: 'POST',
            url: `${constants.ANNOTATION_API}/annotation`,
            withCredentials: true,
            crossdomain : true,
            headers: {
              "Content-Type": "application/json",
              "Accept": "application/json",
            },
            data: {
              annotationText: annotationText,
              endIndex: endIndex,
              itemId: itemId,
              startIndex: startIndex,
              type: type,
              userId: userId
            }
          }
        )
    }

    static async deleteAnnotation(annotationId, userId) {
      return axios.delete(`${constants.ANNOTATION_API}/annotation`, {
        annotationId: annotationId,
        userId: userId
      });
    }
}

export default AnnotationRepository;
