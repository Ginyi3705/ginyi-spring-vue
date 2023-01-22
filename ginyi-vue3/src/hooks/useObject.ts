/**
 * 简单实现对象的深度克隆
 * @param data
 */
export const useDeepClone = (data: object | Array<any>) => {
    const temp = JSON.stringify(data)
    return JSON.parse(temp)
}