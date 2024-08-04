export const Make_debounce = function(fn){
    return async function(queryString,cb){
        const result = await fn(queryString);
        const suggestions = result.map(item=>({value:item}))
        let timeout;
        clearTimeout(timeout)
        timeout = setTimeout(() => {
            cb(suggestions)
        }, 1000)
    }
}