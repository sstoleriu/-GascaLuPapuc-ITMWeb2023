export interface Report {
    id: number
    characteristicsDTO: any
    description: string
    createByUserDTO: any
    createDate: string
    resolveDate: string
    isResolve: boolean
    isAnon: boolean
    base64ph: string
    category: string[]
}