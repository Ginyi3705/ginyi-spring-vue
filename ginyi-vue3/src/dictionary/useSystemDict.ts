import {deptController, postController, roleController} from "@/api";
import {useRemoveEmptyChildrenField} from "@/hooks/useTree";

/**
 * 状态字典
 */
export const useStatusDict: Array<{ label: string, value: number | string }> = [
    {
        label: "正常",
        value: "0",
    },
    {
        label: "禁用",
        value: "1"
    }
]

/**
 * 性别字典
 */
export const useSexDict: Array<{ label: string, value: number | string }> = [
    {
        label: "男",
        value: "0",
    },
    {
        label: "女",
        value: "1"
    },
    {
        label: "未知",
        value: "2"
    }
]

/**
 * 部门字典
 */
export const useDeptDict = (): Promise<Array<any>> => {
    return new Promise((resolve, reject) => {
        deptController.list({}).then(res => {
            resolve(useRemoveEmptyChildrenField(res.data.list))
        }).catch(e => {
            reject(e)
        })
    })
}

/**
 * 角色字典
 */
export const useRoleDict = (): Promise<Array<any>> => {
    return new Promise((resolve, reject) => {
        roleController.list({}).then(res => {
            resolve(res.data.list)
        }).catch(e => {
            reject(e)
        })
    })

}

/**
 * 岗位字典
 */
export const usePostDict = (): Promise<Array<any>> => {
    return new Promise((resolve, reject) => {
        postController.list({}).then(res => {
            resolve(res.data.list)
        }).catch(e => {
            reject(e)
        })
    })
}