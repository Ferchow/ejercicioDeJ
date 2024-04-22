



export function saveData (data){
    //const fs = require('fs');
    /* const finished = (error) => {
      if(error){
        console.error(error)
        return;
      }
    } */
    const jsonData = JSON.stringify(data)
    localStorage.setItem('items',jsonData)
    //fs.writeFile(file, jsonData, finished)
}

//saveData(clientes, '../../../../customers.json')