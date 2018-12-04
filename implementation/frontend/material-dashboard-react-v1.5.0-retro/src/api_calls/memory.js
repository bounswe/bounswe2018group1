import axios from "axios";
import Cookies from "js-cookie";
import constants from "../constants";

const token = Cookies.get("JSESSIONID");
axios.defaults.headers.common["Content-Type"] = "application/json";
axios.defaults.headers.common["JSESSIONID"] = token;

class MemoryRepository {

    static async createMemory(listOfItems, listOfLocations, listOfTags, headline, endDateDD, endDateHH, endDateMM, endDateYYYY, startDateDD, startDateHH, startDateMM, startDateYYYY) {
          //Time format: "2018-11-10T21:17:30.548Z"
          //Processing is done here.
        var currentdate = new Date();

        const response = await axios(
          {
            method: 'POST',
            url: `${constants.API}/memory`,
            withCredentials: true,
            crossdomain : true,
            headers: {
              "Content-Type": "application/json",
              "Accept": "application/json",
            },
            data: {
              listOfItems: listOfItems,
              listOfLocations: listOfLocations,
              listOfTags: listOfTags,
              headline: headline,
              dateOfCreation: currentdate,
              endDateDD: endDateDD,
              endDateHH: endDateHH,
              endDateMM: endDateMM,
              endDateYYYY: endDateYYYY,
              startDateDD: startDateDD,
              startDateHH: startDateHH,
              startDateMM: startDateMM,
              startDateYYYY: startDateYYYY,
              updatedTime: currentdate
            }
          }
        )
    }

    static updateMemory(id, listOfItems, listOfLocations, listOfTags, headline, endDateDD, endDateHH, endDateMM, endDateYYYY, startDateDD, startDateHH, startDateMM, startDateYYYY) {
      var memoryData;
      memoryData.listOfItems = listOfItems;
      memoryData.listOfItems = listOfLocations;
      memoryData.listOfItems = listOfTags;
      memoryData.listOfItems = headline;
      memoryData.listOfItems = endDateDD;
      memoryData.listOfItems = endDateHH;
      memoryData.listOfItems = endDateMM;
      memoryData.listOfItems = endDateYYYY;
      memoryData.listOfItems = startDateDD;
      memoryData.listOfItems = startDateHH;
      memoryData.listOfItems = startDateMM;
      memoryData.listOfItems = startDateYYYY;

      return axios.put(`${constants.API}/memory`, {
        id: id,
        updateMemoryRequestBody: memoryData
      });
    }

    static deleteMemory() {
        //TODO add once implemented on backend.
    }

    // TODO: bunu ayır. Log in olmayan user da görsün
    static getMemory(id) {
      return axios.get(`${constants.API}/memory`, {
        id: id
      });
    }

    static getUserMemories(id) {
      return axios.get(`${constants.API}/memory/user`, {
        id: id
      });
    }

    // TODO: bunu ayır. Log in olmayan user da görsün
    static getAllMemories() {
      return axios.get(`${constants.API}/memory/all`);
    }

}

export default MemoryRepository;
