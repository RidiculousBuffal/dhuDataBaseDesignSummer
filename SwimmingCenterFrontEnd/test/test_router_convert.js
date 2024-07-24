const a = [
    {'id': 1, 'pid': 0},
    {'id': 2, 'pid': 1},
    {'id': 3, 'pid': 1},
    {'id': 4, 'pid': 2},
    {'id': 5, 'pid': 3},
    {'id': 6, 'pid': 0}
]

/*
*
* @params:arr 数组
* @params:pid 当前的父id
* @returns:[] 路由表
* */
function make_array(arr = [], current_pid) {
    const result = []
    for (let i = 0; i < arr.length; i++) {
        if (arr[i].pid === current_pid) {
            let tmp = {
                'id': arr[i].id,
                'pid': arr[i].pid,
                'children': make_array(arr, arr[i].id)
            }
            result.push(tmp)
        }
    }
    return result;
}

let path =  [
        {
            "path": "/user/cardManagement",
            "PID": 0,
            "RID": 3,
            "AID": 11,
            "title": "CardManage",
            "type": "Tab"
        },
        {
            "path": "/user/cardManagement",
            "PID": 11,
            "componentName": "operate.vue",
            "RID": 3,
            "AID": 12,
            "title": "operate",
            "type": "Button"
        },
        {
            "path": "/user/cardManagement",
            "PID": 11,
            "componentName": "query.vue",
            "RID": 3,
            "AID": 13,
            "title": "query",
            "type": "Button"
        },
        {
            "path": "/user/profile",
            "PID": 0,
            "RID": 3,
            "AID": 14,
            "title": "profile",
            "type": "Tab"
        },
        {
            "path": "/user/profile",
            "PID": 14,
            "componentName": "resetPassword.vue",
            "RID": 3,
            "AID": 15,
            "title": "resetPassword",
            "type": "Button"
        },
        {
            "path": "/user/profile",
            "PID": 14,
            "componentName": "profile.vue",
            "RID": 3,
            "AID": 16,
            "title": "profile",
            "type": "Button"
        },
        {
            "path": "/user",
            "PID": 0,
            "componentName": "dashboard.vue",
            "RID": 3,
            "AID": 17,
            "title": "dashboard",
            "type": "Button"
        }
    ]

async function convert_route(arr = [], pid = 0) {
    let result = []
    for (let i = 0; i < arr.length; i++) {
        if (arr[i].PID === pid) {
            if (arr[i].hasOwnProperty("componentName")) {
                let import_path = `@/component${arr[i].path}/${arr[i].componentName}`
                console.log(import_path)
                const component = await import(/* @vite-ignore */ import_path)
                var path;
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
                    'component': component,
                    'children': await convert_route(arr, arr[i].AID)
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
                    'children': await convert_route(arr, arr[i].AID)
                }
                result.push(tmp)
            }
        }
    }
    return result;
}
// const res = make_array(a, 0)
const res =await  convert_route(path,0)
console.log(res)
