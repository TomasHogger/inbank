export type LoadRequest = {
    personalCode: string
    loanAmount: number
    monthPeriod: number
}

export type LoadResponse = {
    approved: boolean,
    maxAmount: number | null
}