export interface Report {
    id: number
    listCategoryOfObject: string[]
    description: string
    listOfCharacteristics: string[]
    createDate: string
    resolveDate: string
    isResolve: boolean
    isAnon: boolean
    base64ph: string
}