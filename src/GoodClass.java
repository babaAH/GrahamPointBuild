public class GoodClass
{
    private final int reqFieldOne;
    private final int reqFieldTwo;
    private final int optFieldOne;
    private final int optFieldTwo;
    private final int optFieldThird;
    private final int optFieldFour;

    public static class Builder
    {
        private final int reqFieldOne;
        private final int reqFieldTwo;

        private int optFieldOne;
        private int optFieldTwo;
        private int optFieldThird;
        private int optFieldFour;

        public Builder(int reqFieldOne, int reqFieldTwo)
        {
            this.reqFieldOne = reqFieldOne;
            this.reqFieldTwo = reqFieldTwo;
        }

        public Builder optFieldOne(int val)
        {
            optFieldOne = val;
            return this;
        }

        public Builder optFieldTwo(int val)
        {
            optFieldTwo = val;
            return this;
        }

        public Builder optFieldThird(int val)
        {
            optFieldThird = val;
            return this;
        }

        public Builder optFieldFour(int val)
        {
            optFieldFour = val;
            return this;
        }

        public GoodClass buildl()
        {
            return  new GoodClass(this);
        }
    }

    private GoodClass(Builder builder)
    {
        reqFieldOne = builder.reqFieldOne;
        reqFieldTwo = builder.reqFieldTwo;

        optFieldOne = builder.optFieldOne;
        optFieldTwo = builder.optFieldTwo;
        optFieldThird = builder.optFieldThird;
        optFieldFour = builder.optFieldFour;
    }
}