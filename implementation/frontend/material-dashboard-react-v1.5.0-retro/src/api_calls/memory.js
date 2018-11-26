import axios from "axios";

import constants from "../constants";

class MemoryRepository {

    static createMemory(listOfItems, listOfLocations, listOfTags, headline, endDateDD, endDateHH, endDateMM, endDateYYYY, startDateDD, startDateHH, startDateMM, startDateYYYY) {
          //Time format: "2018-11-10T21:17:30.548Z"
          //Processing is done here.
        var currentdate = new Date();
        var dateTime = currentdate.getFullYear() + "-" + (currentdate.getMonth()+1)  + "-" + currentdate.getDate() + "T"
          + currentdate.getHours() + ":" + currentdate.getMinutes() + ":" + currentdate.getSeconds();

        return axios.post(`${constants.API}/memory`, {
          listOfItems: JSON.stringify(listOfItems),
          listOfLocations: JSON.stringify(listOfLocations),
          listOfTags: JSON.stringify(listOfTags),
          headline: headline,
          dateOfCreation: dateTime,
          endDateDD: endDateDD,
          endDateHH: endDateHH,
          endDateMM: endDateMM,
          endDateYYYY: endDateYYYY,
          startDateDD: startDateDD,
          startDateHH: startDateHH,
          startDateMM: startDateMM,
          startDateYYYY: startDateYYYY,
          updatedTime: dateTime
        });
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

    static getAllMemories() {
      return axios.get(`${constants.API}/memory/all`);
    }

}

export default MemoryRepository;
