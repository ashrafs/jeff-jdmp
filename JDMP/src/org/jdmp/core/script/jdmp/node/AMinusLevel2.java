/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.jdmp.core.script.jdmp.node;

import org.jdmp.core.script.jdmp.analysis.*;

@SuppressWarnings("nls")
public final class AMinusLevel2 extends PLevel2
{
    private TMinus _minus_;
    private PLevel1 _level1_;

    public AMinusLevel2()
    {
        // Constructor
    }

    public AMinusLevel2(
        @SuppressWarnings("hiding") TMinus _minus_,
        @SuppressWarnings("hiding") PLevel1 _level1_)
    {
        // Constructor
        setMinus(_minus_);

        setLevel1(_level1_);

    }

    @Override
    public Object clone()
    {
        return new AMinusLevel2(
            cloneNode(this._minus_),
            cloneNode(this._level1_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMinusLevel2(this);
    }

    public TMinus getMinus()
    {
        return this._minus_;
    }

    public void setMinus(TMinus node)
    {
        if(this._minus_ != null)
        {
            this._minus_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._minus_ = node;
    }

    public PLevel1 getLevel1()
    {
        return this._level1_;
    }

    public void setLevel1(PLevel1 node)
    {
        if(this._level1_ != null)
        {
            this._level1_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._level1_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._minus_)
            + toString(this._level1_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._minus_ == child)
        {
            this._minus_ = null;
            return;
        }

        if(this._level1_ == child)
        {
            this._level1_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._minus_ == oldChild)
        {
            setMinus((TMinus) newChild);
            return;
        }

        if(this._level1_ == oldChild)
        {
            setLevel1((PLevel1) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
