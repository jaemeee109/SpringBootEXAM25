async function uploadToServer(formObj){
    console.log("upload.js :서버 업로드 처리")
    console.log("upload.js : [uploadToServer]")
    console.log(formObj)

    const response = await axios({
        method : 'post',
        url : '/upload',
        data : formObj,
        headers : {
            'Content-Type':'multipart/form-data',
        },
    })
    return response.data

} // uploadToServer 종료

async function removeFileToServer(uuid, fileName){
    const response = await axios.delete(`/remove/${uuid}_${fileName}`)
    return response.data
}