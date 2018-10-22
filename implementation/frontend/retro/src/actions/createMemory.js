import fetch from 'isomorphic-fetch';
import Constants from '../constants.js';

export function createMemory(data) {
    return fetch(Constants.API, {
        method: 'POST',
        mode: 'CORS',
        body: JSON.stringify(data),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(res => {
        return res;
    }).catch(err => err);
}
