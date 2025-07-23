async function get1(bno){
   

    const result = await axios.get(`/replies/list/${bno}`)
    
    return result;
}


async function getList({bno, page, size, goLast}){  
 
    const result = await axios.get(`/replies/list/${bno}`, {params:{page, size}})

  
    if(goLast){
        const total = result.data.total // 총댓글수
        const lastPage = parseInt(Math.ceil(total/size))

        return getList({bno:bno, page:lastPage, size:size})
        
    } // if 종료

    return result.data
} // getList 종료

async function addReply(replyObj) {

    const response = await axios.post(`/replies/`,replyObj)
    return response.data

} // addReply 종료

async function getReply(rno) {

    const response = await axios.get(`/replies/${rno}`)
    return response.data

} // getReply 종료

async function modifyReply(replyObj) {

    const response = await axios.put(`/replies/${replyObj.rno}`, replyObj)
    return response.data

} // modifyReply 종료

async function removeReply(rno) {

    const response = await axios.delete(`/replies/${rno}`)
    return response.data

} // removeReply 종료