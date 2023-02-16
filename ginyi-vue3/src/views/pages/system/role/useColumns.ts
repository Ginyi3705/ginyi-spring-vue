import {useStaticDict} from "@/dictionary/useStaticDict";

export const useColumns = () => {
    const {statusDict} = useStaticDict()


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