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
        }
        result = []
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