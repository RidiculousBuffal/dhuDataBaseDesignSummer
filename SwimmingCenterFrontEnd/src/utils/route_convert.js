const AllComponents =import.meta.glob("../component/**/*.vue")
export async function convert_route(arr = [], pid = 0) {
    let result = []
    for (let i = 0; i < arr.length; i++) {
        if (arr[i].PID === pid) {
            if (arr[i].hasOwnProperty("componentName")) {
                let import_path = '../component'+arr[i].path+'/'+arr[i].componentName
                console.log(import_path)
                console.log(AllComponents[import_path])
                var path = '';
                if (pid === 0) {
                    path = '/' + arr[i].componentName.replace('.vue', '')
                } else {
                    path = arr[i].componentName.replace('.vue', '')
                }
                let tmp = {
                    'path': path,
                    'name': arr[i].title,
                    'meta': {
                        'title': arr[i].title,
                        'type': arr[i].type
                    },
                    'component': AllComponents[import_path],
                    'children':  await convert_route(arr, arr[i].AID)
                }
                result.push(tmp)
            } else {
                let tmp = {
                    'path': arr[i].path,
                    'name': arr[i].title,
                    'meta': {
                        'title': arr[i].title,
                        'type': arr[i].type
                    },
                    'children':await convert_route(arr, arr[i].AID)
                }
                result.push(tmp)
            }
        }
    }
    return result;
}
