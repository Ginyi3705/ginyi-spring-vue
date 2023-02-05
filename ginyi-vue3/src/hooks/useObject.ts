/**
 * 简单实现对象的深度克隆
 * 注意：
 *      1、使用该方式会出现一些问题，如值为undefined、函数、Date类型等属性会无法被克隆，此处待完善！！！
 *      2、非得使用undefined的话，可以使用null替代
 * @param data
 */
export const useDeepClone = (data: object | Array<any>) => {
    const temp = JSON.stringify(data)
    return JSON.parse(temp)
}