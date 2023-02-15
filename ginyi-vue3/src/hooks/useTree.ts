/**
 *
 * @param name 子节点 name
 * @param list  要查找的 list
 * @param result  结果集
 * @returns {boolean|*[]}
 */
import {useDeepClone} from "@/hooks/useObject";

export const useFindParentName = (name: string, list: Array<any> = [], result: Array<string> = []): Array<string> | undefined => {
    for (let i = 0; i < list.length; i++) {
        const item = list[i];
        if (item.name === name) {
            return result;
        }
        if (item.children?.length > 0) {
            result.push(item.name)
            const temp = useFindParentName(name, item.children, result);
            if (temp !== undefined) {
                return result;
            }
            result = []
        }
    }
}

/**
 * 根据 name 查找匹配的 node
 *
 * @param name
 * @param list
 */
export const useFindNodeByName = (name: string, list: Array<any>): any => {
    for (let i = 0; i < list.length; i++) {
        const item = list[i];
        if (item.name === name) {
            return item;
        } else {
            if (item.children?.length > 0) {
                const temp = useFindNodeByName(name, item.children);
                if (temp) {
                    return temp;
                }
            }
        }
    }
}


/**
 *
 * @param name 子节点 name
 * @param list  要查找的 list
 * @param result  结果集
 * @returns {boolean|*[]}
 */
export const useFindParentNodes = (name: string,
                                   list: Array<any> = [],
                                   result: Array<any> = []): Array<any> | undefined => {
    for (let i = 0; i < list.length; i++) {
        const item = list[i];
        result.push(item);
        if (item.name === name) {
            return result;
        }
        if (item.children?.length > 0) {
            const temp = useFindParentNodes(name, item.children, result);
            if (temp !== undefined) {
                // 表示找到了 name 匹配的, 替换 result 中的最后一项 (最后一项保存的是同级别下的上一项)
                if (temp.length === 1) {
                    result[result.length - 1] = temp[0];
                }
                return result;
            }
        }
        result.pop()
    }
}


/**
 * 格式化树
 */
export const useRemoveEmptyChildrenField = (list: Array<any>, fieldName: string = "children"): Array<any> => {
    return list.map((item: any) => {
        if (item[fieldName]?.length > 0) {
            useRemoveEmptyChildrenField(item[fieldName], fieldName)
        } else {
            delete item[fieldName]
        }
        return item
    })
}

/**
 * 数组扁平化
 * @param list
 * @param result
 * @param fieldName
 */
export const useTreeToArray = (list: Array<any>, result: Array<any> = [], fieldName: string = "children"): Array<any> => {
    list.map(item => {
        result.push(item)
        if(item[fieldName]?.length > 0){
            useTreeToArray(item[fieldName], result)
        }
    })
    return result
}