import axios from "axios";
import Cookies from "js-cookie";
import constants from "../constants";

const token = Cookies.get("JSESSIONID");
axios.defaults.headers.common["Content-Type"] = "application/json";
axios.defaults.headers.common["JSESSIONID"] = token;

//Change once implemented
class Like_CommentRepository {

    static async comment(comment, memoryId) {
      return axios.post(`${constants.API}/memory/comment`, {
        comment: comment,
        memoryId: memoryId
      });
    }

    static async deleteComment(commentId) {
      return axios.delete(`${constants.API}/memory/comment`, {
        commentId: commentId
      });
    }

    static async like(memoryId) {
      //console.log(typeof memoryId);
      return axios.post(`${constants.API}/memory/like` + '?memoryId=' + memoryId);
    }

    static async unlike(memoryId) {
      return axios.post(`${constants.API}/memory/unlike`+ '?memoryId=' + memoryId);
    }
}

export default Like_CommentRepository;
