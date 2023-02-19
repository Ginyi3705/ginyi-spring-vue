import {useStaticDict} from "@/dictionary/useStaticDict";

export const useCommonColumns = () => {
    const {statusDict} = useStaticDict()


    /**
     * 渲染状态
     * @param stateId
     */
    const useRenderStateById = (stateId: string) => {
        const temp = statusDict.value.filter(state => {
            return state.value === stateId
        })
        return temp.length !== 0 ? temp[0].label : undefined;
    }

    return {
        useRenderStateById,
    }
}