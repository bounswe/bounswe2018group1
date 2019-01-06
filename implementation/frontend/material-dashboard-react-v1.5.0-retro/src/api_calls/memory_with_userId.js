import axios from "axios";
import Cookies from "js-cookie";
import constants from "../constants";

const token = Cookies.get("JSESSIONID");
axios.defaults.headers.common["Content-Type"] = "application/json";
axios.defaults.withCredentials = true;

class MemoryRepository {

    static async createMemory(memory) {
        //Time format: "2018-11-10T21:17:30.548Z"
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
            data: memory
          }
        )
    }

    static async updateMemory(id, listOfItems, listOfLocations, listOfTags, headline, endDateDD, endDateHH, endDateMM, endDateYYYY, startDateDD, startDateHH, startDateMM, startDateYYYY) {
      var memoryData;
      memoryData.listOfItems = listOfItems;
      memoryData.listOfLocations = listOfLocations;
      memoryData.listOfTags = listOfTags;
      memoryData.headline = headline;
      memoryData.endDateDD = endDateDD;
      memoryData.endDateHH = endDateHH;
      memoryData.endDateMM = endDateMM;
      memoryData.endDateYYYY = endDateYYYY;
      memoryData.startDateDD = startDateDD;
      memoryData.startDateHH = startDateHH;
      memoryData.startDateMM = startDateMM;
      memoryData.startDateYYYY = startDateYYYY;

      return axios.put(`${constants.API}/memory`, {
        id: id,
        updateMemoryRequestBody: memoryData
      });
    }

    static async deleteMemory(id) {
      return axios.delete(`${constants.API}/memory`, {
        id: id
      });
    }

    static async getUserMemories(userID) {
      return axios.get(`${constants.API}/memory/user`, {
        id: userID
      });
    }
}

export default MemoryRepository;
