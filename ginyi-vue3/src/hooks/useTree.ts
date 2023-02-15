/**
 *
 * @param name 子节点 name
 * @param list  要查找的 list
 * @param result  结果集
 * @returns {boolean|*[]}
 */

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
 * 根据子节点指定的key和对应的value，向上查找其父级
 * @param key 如 userId: 123 中的 userId
 * @param value 如 userId: 123 中的 123
 * @param childrenField
 * @param list
 * @param result
 */
export const useFindParentNodes = (key: string,
                                   value: string | number,
                                   childrenField: string = "children",
                                   list: Array<any> = [],
                                   result: Array<any> = []): Array<any> | undefined => {
    for (let i = 0; i < list.length; i++) {
        const item = list[i];
        result.push(item);
        if (item[key] === value) {
            return result;
        }
        if (item.children?.length > 0) {
            const temp = useFindParentNodes(key, value, childrenField, item.children, result);
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
export const useRemoveEmptyChildrenField = (list: Array<any>, childrenField: string = "children"): Array<any> => {
    return list.map((item: any) => {
        if (item[childrenField]?.length > 0) {
            useRemoveEmptyChildrenField(item[childrenField], childrenField)
        } else {
            delete item[childrenField]
        }
        return item
    })
}

/**
 * 数组扁平化
 * @param list
 * @param result
 * @param childrenField
 */
export const useTreeToArray = (list: Array<any>, result: Array<any> = [], childrenField: string = "children"): Array<any> => {
    list.map(item => {
        result.push(item)
        if (item[childrenField]?.length > 0) {
            useTreeToArray(item[childrenField], result)
        }
    })
    return result
}


/**
 * 搭配 useFindParentNodes 使用（先查找出所有的父级节点）
 * 数组分割显示 如 总公司 / 市场部门 / 销售岗
 */

export const useArraySeparator = (arr: Array<any>, key: string, separator: string = "/") => {
    let result: string = "";
    arr.forEach((item, index) => {
        if (index === arr.length - 1) {
            result = `${result} ${item[key]}`
        } else {
            result = `${result} ${item[key]} ${separator}`
        }
    })
    return result;
}